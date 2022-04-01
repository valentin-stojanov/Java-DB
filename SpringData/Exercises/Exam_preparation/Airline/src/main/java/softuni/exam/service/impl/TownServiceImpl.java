package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.Town;
import softuni.exam.models.dto.TownImportDTO;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {
    private static final Path TOWNS_FILE_PATH = Path.of("src/main/resources/files/json/towns.json");

    private final TownRepository townRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository,
                           Gson gson,
                           ValidationUtil validationUtil,
                           ModelMapper modelMapper) {
        this.townRepository = townRepository;

        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        BufferedReader reader = Files.newBufferedReader(TOWNS_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importTowns() throws IOException {
        TownImportDTO[] townImportDTOS = this.gson.fromJson(readTownsFileContent(), TownImportDTO[].class);

        StringBuilder result = new StringBuilder();
        for (TownImportDTO townImportDTO : townImportDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(townImportDTO);
            if(!isValidDTO){
                result
                        .append("Invalid town")
                        .append(System.lineSeparator());
                continue;
            }
            Town town = this.modelMapper.map(townImportDTO, Town.class);
            this.townRepository.save(town);
            result
                    .append(String.format("Successfully imported %s - %d", town.getName(), town.getPopulation()))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }
}
