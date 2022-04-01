package softuni.exam.instagraphlite.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PictureImportDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.service.PictureService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    private static final Path PICTURE_FILE_PATH = Path.of("src/main/resources/files/pictures.json");

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository,
                              ModelMapper modelMapper,
                              Gson gson,
                              ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;

        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() >0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        BufferedReader reader = Files.newBufferedReader(PICTURE_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importPictures() throws IOException {
        PictureImportDTO[] pictureImportDTOS = this.gson.fromJson(readFromFileContent(), PictureImportDTO[].class);

        StringBuilder result = new StringBuilder();
        for (PictureImportDTO pictureImportDTO : pictureImportDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(pictureImportDTO);
            if (!isValidDTO){
                result.append("Invalid Picture\n");
                continue;
            }
            Picture picture = this.modelMapper.map(pictureImportDTO, Picture.class);
            result.append(String.format("Successfully imported Picture, with size %.2f\n", picture.getSize()));
        }
        return result.toString();
    }

    @Override
    public String exportPictures() {
        return null;
    }
}
