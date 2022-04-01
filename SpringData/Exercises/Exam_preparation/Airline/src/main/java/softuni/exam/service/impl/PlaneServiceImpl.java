package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Plane;
import softuni.exam.models.dto.PlainsImportDTO;
import softuni.exam.models.dto.PlaneDTO;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlaneServiceImpl implements PlaneService {
    public static final Path PLANE_FILE_PATH = Path.of("src/main/resources/files/xml/planes.xml");

    private final PlaneRepository planeRepository;
    private final ModelMapper modelMapper;
    private final JAXBContext jaxbContext;
    private final ValidationUtil validationUtil;

    public PlaneServiceImpl(PlaneRepository planeRepository,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtil) throws JAXBException {
        this.planeRepository = planeRepository;

        this.modelMapper = modelMapper;
        jaxbContext = JAXBContext.newInstance(PlainsImportDTO.class);
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        BufferedReader xmlReader = Files.newBufferedReader(PLANE_FILE_PATH);
        return xmlReader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importPlanes() throws JAXBException, IOException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader xmlReader = Files.newBufferedReader(PLANE_FILE_PATH);

        PlainsImportDTO plainsImportDTO = (PlainsImportDTO) unmarshaller.unmarshal(xmlReader);
        List<PlaneDTO> planeDTOS = plainsImportDTO.getPlanes();

        StringBuilder result =new StringBuilder();
        for (PlaneDTO planeDTO : planeDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(planeDTO);
            if (!isValidDTO){
                result
                        .append("Invalid plane")
                        .append(System.lineSeparator());
                continue;
            }
            Plane plane = this.modelMapper.map(planeDTO, Plane.class);
            this.planeRepository.save(plane);
            result
                    .append(String.format("Successfully imported Plane %s", plane.getRegisterNumber()))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }
}
