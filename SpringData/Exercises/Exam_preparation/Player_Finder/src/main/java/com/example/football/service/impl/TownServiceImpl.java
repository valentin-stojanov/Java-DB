package com.example.football.service.impl;

import com.example.football.model.dto.ImportTownDTO;
import com.example.football.model.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final Gson gson;
    private final ModelMapper mapper;
    private final Validator validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) {
        this.townRepository = townRepository;

        this.gson = new GsonBuilder()
                .create();
        this.mapper = new ModelMapper();

        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }


    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/json/towns.json");

        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importTowns() throws IOException {

        String json = readTownsFileContent();

        ImportTownDTO[] importTownDTOS = this.gson.fromJson(json, ImportTownDTO[].class);

        List<String> result = new ArrayList<>();

        for (ImportTownDTO importTownDTO : importTownDTOS) {
            Set<ConstraintViolation<ImportTownDTO>> violationSet =
                    validator.validate(importTownDTO);

            if (violationSet.isEmpty()){
                Optional<Town> optionalTown = this.townRepository.findByName(importTownDTO.getName());
                if (optionalTown.isPresent()){
                    result.add("Invalid Town");
                } else  {
                    Town town = this.mapper.map(importTownDTO, Town.class);
                    this.townRepository.save(town);
                    result.add(String.format("Successfully imported Town %s - %d",
                            town.getName(), town.getPopulation()));
                }
            } else {
                result.add("Invalid Town");
            }
        }

        return String.join("\n", result);
    }
}
