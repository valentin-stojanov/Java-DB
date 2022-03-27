package com.example.football.service.impl;

import com.example.football.model.dto.ImportStatDTO;
import com.example.football.model.dto.ImportStatsDTO;
import com.example.football.model.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;
    private final JAXBContext jaxbContext;
    private final ModelMapper mapper;
    private final Validator validator;

    @Autowired
    public StatServiceImpl(StatRepository statRepository) throws JAXBException {
        this.statRepository = statRepository;
        this.jaxbContext = JAXBContext.newInstance(ImportStatsDTO.class);
        this.mapper = new ModelMapper();
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public boolean areImported() {
        return statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        Path xml = Path.of("src/main/resources/files/xml/stats.xml");
        return String.join("\n" ,Files.readAllLines(xml));
    }

    @Override
    public String importStats() throws JAXBException, IOException {
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        BufferedReader xmlReader = Files.newBufferedReader(Path.of("src/main/resources/files/xml/stats.xml"));

        ImportStatsDTO statsDTO = (ImportStatsDTO) unmarshaller.unmarshal(xmlReader);
        List<ImportStatDTO> statList = statsDTO.getStats();

        List<String> result = new ArrayList<>();
        for (ImportStatDTO statDTO : statList) {
            Set<ConstraintViolation<ImportStatDTO>> violationSet = this.validator.validate(statDTO);

            if (violationSet.isEmpty()){

                Optional<Stat> optionalStat = this.statRepository.findByPassingAndShootingAndEndurance(
                        statDTO.getPassing(), statDTO.getShooting(), statDTO.getEndurance());

                if (optionalStat.isPresent()){
                    result.add("Invalid Stat");
                } else {
                    Stat stat = this.mapper.map(statDTO, Stat.class);
                    this.statRepository.save(stat);
                    result.add(String.format("Successfully imported Stat %.2f - %.2f - %.2f",
                            stat.getPassing(), stat.getShooting(), stat.getEndurance()));
                }

            } else {
                result.add("Invalid Stat");
            }
        }
        return String.join("\n", result);
    }
}
