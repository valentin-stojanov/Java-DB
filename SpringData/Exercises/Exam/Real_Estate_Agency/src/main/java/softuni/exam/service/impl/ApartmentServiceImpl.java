package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentImportDto;
import softuni.exam.models.dto.ApartmentsImportDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtil;

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
public class ApartmentServiceImpl implements ApartmentService {
    public static final Path APARTMENT_FILE_PATH = Path.of("src/main/resources/files/xml/apartments.xml");

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final JAXBContext jaxbContext;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository,
                                ModelMapper modelMapper,
                                ValidationUtil validationUtil,
                                TownRepository townRepository) throws JAXBException {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;

        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        jaxbContext = JAXBContext.newInstance(ApartmentsImportDTO.class);
    }

    @Override
    public boolean areImported() {
        return this.apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        BufferedReader xmlReader = Files.newBufferedReader(APARTMENT_FILE_PATH);
        return xmlReader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader xmlReader = Files.newBufferedReader(APARTMENT_FILE_PATH);

        ApartmentsImportDTO apartmentsImportDTO = (ApartmentsImportDTO) unmarshaller.unmarshal(xmlReader);
        List<ApartmentImportDto> apartmentImportDtos = apartmentsImportDTO.getApartments();

        StringBuilder result = new StringBuilder();
        for (ApartmentImportDto apartmentImportDto : apartmentImportDtos) {
            boolean isValidDTO = this.validationUtil.isValid(apartmentImportDto);
            if (!isValidDTO){
                result
                        .append("Invalid apartment")
                        .append(System.lineSeparator());
                continue;
            }

            Optional<Apartment> optionalApartment = this.apartmentRepository.findByTownNameAndArea(apartmentImportDto.getTown(), apartmentImportDto.getArea());
            if (optionalApartment.isPresent()){
                result
                        .append("Invalid apartment")
                        .append(System.lineSeparator());
                continue;
            }
            Apartment apartment = this.modelMapper.map(apartmentImportDto, Apartment.class);
            Town town = this.townRepository.findByTownName(apartmentImportDto.getTown()).get();
            apartment.setTown(town);
            this.apartmentRepository.save(apartment);

            result.append(String.format("Successfully imported apartment %s - %.2f",
                    apartment.getApartmentType(), apartment.getArea()))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }
}
