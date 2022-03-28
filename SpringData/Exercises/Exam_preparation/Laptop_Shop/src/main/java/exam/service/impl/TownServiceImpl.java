package exam.service.impl;

import exam.model.dtos.ImportTownDTO;
import exam.model.dtos.ImportTownsDTO;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;
    private final JAXBContext jaxbContext;
    private final ModelMapper modelMapper;
    private final Path TOWNS_PATH = Path.of("src/main/resources/files/xml/towns.xml");
    private final Validator validator;

    @Autowired
    public TownServiceImpl(TownRepository townRepository) throws JAXBException {
        this.townRepository = townRepository;

        this.jaxbContext = JAXBContext.newInstance(ImportTownsDTO.class);
        this.modelMapper = new ModelMapper();
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
        BufferedReader xmlReader = Files.newBufferedReader(TOWNS_PATH);

        return xmlReader
                .lines()
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String importTowns() throws JAXBException, IOException {
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        ImportTownsDTO importTownsDTO = (ImportTownsDTO) unmarshaller.unmarshal(Files.newBufferedReader(TOWNS_PATH));

        List<ImportTownDTO> towns = importTownsDTO.getTowns();
        StringBuilder result = new StringBuilder();
        for (ImportTownDTO townDTO : towns) {
            Set<ConstraintViolation<ImportTownDTO>> violationSet = validator.validate(townDTO);

            if (violationSet.isEmpty()) {
                Town town = this.modelMapper.map(townDTO, Town.class);
                this.townRepository.save(town);
                result.append(String.format("Successfully imported Town %s\n", town.getName()));
            } else {
                result.append("Invalid town\n");
            }
        }
        return result.toString();
    }
}
