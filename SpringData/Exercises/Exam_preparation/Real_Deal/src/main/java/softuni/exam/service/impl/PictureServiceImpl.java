package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PictureImportDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    public static final Path PICTURE_FILE_PATH = Path.of("src/main/resources/files/json/pictures.json");

    private final PictureRepository pictureRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CarRepository carRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,
                              Gson gson,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil,
                              CarRepository carRepository) {
        this.pictureRepository = pictureRepository;
        this.carRepository = carRepository;

        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        BufferedReader reader = Files.newBufferedReader(PICTURE_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String importPictures() throws IOException {
        PictureImportDTO[] pictureImportDTOS = this.gson.fromJson(readPicturesFromFile(), PictureImportDTO[].class);

        StringBuilder result = new StringBuilder();
        for (PictureImportDTO pictureImportDTO : pictureImportDTOS) {
            boolean isValidDto = this.validationUtil.isValid(pictureImportDTO);

            if (!isValidDto){
                result.append("Invalid picture\n");
                continue;
            }

            long carId = pictureImportDTO.getCar();

            Optional<Car> optionalCar = this.carRepository.findById(carId);

            Picture picture = this.modelMapper.map(pictureImportDTO, Picture.class);
            picture.setCar(optionalCar.get());

            this.pictureRepository.save(picture);

            result.append(String.format("Successfully import picture - %s\n", picture.getName()));
        }
        return result.toString();
    }
}
