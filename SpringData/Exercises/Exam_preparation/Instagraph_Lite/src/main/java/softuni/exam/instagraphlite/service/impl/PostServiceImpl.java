package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.PostDTO;
import softuni.exam.instagraphlite.models.dto.PostsImportDTO;
import softuni.exam.instagraphlite.models.entity.Picture;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.models.entity.User;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private static final Path POST_FILE_PATH = Path.of("src/main/resources/files/posts.xml");
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final JAXBContext jaxbContext;
    private final PictureRepository pictureReposirory;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           ValidationUtil validationUtil,
                           PictureRepository pictureReposirory,
                           UserRepository userRepository) throws JAXBException {
        this.postRepository = postRepository;
        this.pictureReposirory = pictureReposirory;
        this.userRepository = userRepository;

        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        jaxbContext = JAXBContext.newInstance(PostsImportDTO.class);
    }


    @Override
    public boolean areImported() {
        return this.postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        BufferedReader reader = Files.newBufferedReader(POST_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        BufferedReader xmlReader = Files.newBufferedReader(POST_FILE_PATH);
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        PostsImportDTO postsImportDTO = (PostsImportDTO) unmarshaller.unmarshal(xmlReader);

        List<PostDTO> postDTOS = postsImportDTO.getPosts();

        StringBuilder result = new StringBuilder();
        for (PostDTO postDTO : postDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(postDTO);
            if(!isValidDTO){
                result.append("Invalid Post\n");
                continue;
            }
            String picturePath = postDTO.getPicture().getPath();
            Optional<Picture> optionalPicture = this.pictureReposirory.findByPath(picturePath);
            if (optionalPicture.isEmpty()){
                result.append("Invalid Post\n");
                continue;
            }
            String userName = postDTO.getUser().getUsername();
            Optional<User> optionalUser = this.userRepository.findByUsername(userName);
            if(optionalUser.isEmpty()){
                result.append("Invalid Post\n");
                continue;
            }

            Post post = this.modelMapper.map(postDTO, Post.class);
            post.setPicture(optionalPicture.get());
            post.setUser(optionalUser.get());
            this.postRepository.save(post);

            result.append(String.format("Successfully imported Post, made by %s\n", postDTO.getUser().getUsername()));
        }
        return result.toString();
    }
}
