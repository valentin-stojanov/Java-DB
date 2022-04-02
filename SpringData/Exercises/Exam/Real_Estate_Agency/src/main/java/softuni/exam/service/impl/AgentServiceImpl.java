package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.inline.StandardHTMLInliner;
import softuni.exam.models.dto.AgentImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentServiceImpl implements AgentService {
    public static final Path AGENT_FILE_PATH = Path.of("src/main/resources/files/json/agents.json");

    private final AgentRepository agentRepository;
    private final TownRepository townRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository,
                            Gson gson,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtil,
                            TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;

        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        BufferedReader reader = Files.newBufferedReader(AGENT_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importAgents() throws IOException {
        AgentImportDTO[] agentImportDTOS = this.gson.fromJson(readAgentsFromFile(), AgentImportDTO[].class);

        StringBuilder result = new StringBuilder();
        for (AgentImportDTO agentImportDTO : agentImportDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(agentImportDTO);
            if(!isValidDTO){
                result
                        .append("Invalid agent")
                        .append(System.lineSeparator());
                continue;
            }
            Optional<Agent> optionalAgent = this.agentRepository.findByFirstName(agentImportDTO.getFirstName());
            if (optionalAgent.isPresent()){
                result
                        .append("Invalid agent")
                        .append(System.lineSeparator());
                continue;
            }

            Optional<Town> optionalTown = this.townRepository.findByTownName(agentImportDTO.getTown());

            Agent agent = this.modelMapper.map(agentImportDTO, Agent.class);
            agent.setTown(optionalTown.get());
            this.agentRepository.save(agent);
            result
                    .append(String.format("Successfully imported agent - %s %s",agent.getFirstName(), agent.getLastName() ))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }
}
