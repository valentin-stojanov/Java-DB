package com.example.football.service.impl;

import com.example.football.model.dto.ImportPlayerDTO;
import com.example.football.model.dto.ImportPlayersDTO;
import com.example.football.model.entity.Player;
import com.example.football.model.entity.Stat;
import com.example.football.model.entity.Team;
import com.example.football.model.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final JAXBContext jaxbContext;
    private final ModelMapper mapper;
    private final Validator validator;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final StatRepository statrepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, TownRepository townRepository, StatRepository statrepository) throws JAXBException {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.statrepository = statrepository;

        this.jaxbContext = JAXBContext.newInstance(ImportPlayersDTO.class);
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
        this.mapper = new ModelMapper();

        this.mapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);
//        (Converter<String, LocalDate>) mappingContext -> LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        List<String> strings = Files.readAllLines(Path.of("src/main/resources/files/xml/players.xml"));
        return String.join("\n", strings);
    }

    @Override
    public String importPlayers() throws IOException, JAXBException {
        BufferedReader reader = Files.newBufferedReader(Path.of("src/main/resources/files/xml/players.xml"));
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();

        ImportPlayersDTO unmarshal = (ImportPlayersDTO) unmarshaller.unmarshal(reader);

        List<ImportPlayerDTO> players = unmarshal.getPlayers();

        List<String> result = new ArrayList<>();
        for (ImportPlayerDTO playerDTO : players) {
            Set<ConstraintViolation<ImportPlayerDTO>> violationSet = validator.validate(playerDTO);
            String playerDTOEmail = playerDTO.getEmail();
            Optional<Player> optionalPlayer = this.playerRepository.findByEmail(playerDTOEmail);

            if (violationSet.isEmpty() && optionalPlayer.isEmpty()){

                Player player = this.mapper.map(playerDTO, Player.class);

                String playerTeam = playerDTO.getTeam().getName();
                long playerStatId = playerDTO.getStat().getId();
                String playerTown = playerDTO.getTown().getName();

                Optional<Team> optionalTeam = this.teamRepository.findByName(playerTeam);
                player.setTeam(optionalTeam.get());

                Optional<Town> optionalTown = this.townRepository.findByName(playerTown);
                player.setTown(optionalTown.get());

                Optional<Stat> optionalStat = this.statrepository.findById(playerStatId);
                player.setStat(optionalStat.get());

                this.playerRepository.save(player);
                result.add(String.format("Successfully imported Player %s %s - %s",
                        player.getFirstname(), player.getLastName(), player.getPosition()));

            } else {
                result.add("Invalid Player");
            }

        }

        return String.join("\n", result);
    }

    @Override
    public String exportBestPlayers() {
        return null;
    }
}
