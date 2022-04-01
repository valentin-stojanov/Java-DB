package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.UserExportDTO;
import softuni.exam.instagraphlite.models.dto.UserImportDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.UserService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Path USERS_FILE_PATH = Path.of("src/main/resources/files/users.json");
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final PictureRepository pictureReposirory;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           Gson gson,
                           ValidationUtil validationUtil,
                           PictureRepository pictureReposirory) {
        this.userRepository = userRepository;
        this.pictureReposirory = pictureReposirory;

        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        BufferedReader reader = Files.newBufferedReader(USERS_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importUsers() throws IOException {
        UserImportDTO[] userImportDtos = this.gson.fromJson(readFromFileContent(), UserImportDTO[].class);

        StringBuilder result = new StringBuilder();
        for (UserImportDTO userImportDto : userImportDtos) {
            boolean isValidDTO = this.validationUtil.isValid(userImportDto);
            if (!isValidDTO){
                result.append("Invalid user\n");
                continue;
            }
            String picturePath = userImportDto.getProfilePicture();
            Optional<Picture> optionalPicture = this.pictureReposirory.findByPath(picturePath);
            if (optionalPicture.isEmpty()){
                result.append("Invalid user\n");
                continue;
            }

            User user = this.modelMapper.map(userImportDto, User.class);
            user.setProfilePicture(optionalPicture.get());

            this.userRepository.save(user);
            result.append(String.format("Successfully imported User: %s\n", user.getUsername()));
        }
        return result.toString();
    }

    @Override
    public String exportUsersWithTheirPosts() {
        List<UserExportDTO> userExportDTOS = this.userRepository.orderAllUsers();
        String collect = userExportDTOS
                .stream()
                .map(d -> d.userInfo())
                .collect(Collectors.joining(System.lineSeparator()));
        return collect;
    }
}
