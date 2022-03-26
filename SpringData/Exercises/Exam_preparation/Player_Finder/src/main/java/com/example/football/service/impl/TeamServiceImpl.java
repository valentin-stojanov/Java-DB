package com.example.football.service.impl;

import com.example.football.model.dto.ImportTeamDTO;
import com.example.football.model.entity.Team;
import com.example.football.model.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
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
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final Gson gson;
    private final TownRepository townRepository;
    private final ModelMapper mapper;
    private final Validator validator;


    public TeamServiceImpl(TeamRepository teamRepository, TownRepository townRepository) {
        this.teamRepository = teamRepository;
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
        return teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        Path path = Path.of("src/main/resources/files/json/teams.json");
        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importTeams() throws IOException {
        String json = readTeamsFileContent();

        ImportTeamDTO[] importTeamDTOs =
                this.gson.fromJson(json, ImportTeamDTO[].class);

        List<String> result = new ArrayList<>();
        for (ImportTeamDTO importTeamDTO : importTeamDTOs) {
            Set<ConstraintViolation<ImportTeamDTO>> violationSet = validator.validate(importTeamDTO);

            if (violationSet.isEmpty()) {
                String teamName = importTeamDTO.getName();

                Optional<Team> optionalTeam = this.teamRepository.findByName(teamName);

                if (optionalTeam.isEmpty()) {
                    String townName = importTeamDTO.getTownName();
                    Optional<Town> optionalTown = this.townRepository.findByName(townName);

                    Team team = this.mapper.map(importTeamDTO, Team.class);
                    team.setTown(optionalTown.get());

                    this.teamRepository.save(team);

                    result.add(String.format("Successfully imported Team %s - %d", team.getName(), team.getFanBase()));
                } else {
                    result.add("Invalid Team");
                }
            } else {
                result.add("Invalid Team");
            }
        }
        return String.join("\n", result);
    }
}
