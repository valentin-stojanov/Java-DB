package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferExportDTO;
import softuni.exam.models.dto.OfferImportDTO;
import softuni.exam.models.dto.OffersImportDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
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
public class OfferServiceImpl implements OfferService {
    public static final Path OFFERS_FILE_PATH = Path.of("src/main/resources/files/xml/offers.xml");

    private final OfferRepository offerRepository;
    private final ApartmentRepository apartmentRepository;
    private final AgentRepository agentRepository;

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final JAXBContext jaxbContext;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtil,
                            AgentRepository agentRepository,
                            ApartmentRepository apartmentRepository) throws JAXBException {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;

        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        jaxbContext = JAXBContext.newInstance(OffersImportDTO.class);
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        BufferedReader xmlReader = Files.newBufferedReader(OFFERS_FILE_PATH);
        return xmlReader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader xmlReader = Files.newBufferedReader(OFFERS_FILE_PATH);

        OffersImportDTO offersImportDTO = (OffersImportDTO) unmarshaller.unmarshal(xmlReader);
        List<OfferImportDTO> offerImportDTOS = offersImportDTO.getOffers();

        StringBuilder result = new StringBuilder();
        for (OfferImportDTO offerImportDTO : offerImportDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(offerImportDTO);
            if (!isValidDTO){
                result
                        .append("Invalid offer")
                        .append(System.lineSeparator());
                continue;
            }

            Optional<Agent> optionalAgent = this.agentRepository.findByFirstName(offerImportDTO.getAgent().getName());
            if (optionalAgent.isEmpty()){
                result
                        .append("Invalid offer")
                        .append(System.lineSeparator());
                continue;
            }
            Optional<Apartment> optionalApartment = this.apartmentRepository.findById(offerImportDTO.getApartment().getId());
            Offer offer = this.modelMapper.map(offerImportDTO, Offer.class);

            offer.setAgent(optionalAgent.get());
            offer.setApartment(optionalApartment.get());
            this.offerRepository.save(offer);
            result.append(String.format("Successfully imported offer %.2f", offer.getPrice()))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String exportOffers() {
        List<OfferExportDTO> theBestOffers = this.offerRepository.getTheBestOffers();
        return theBestOffers
                .stream()
                .map(OfferExportDTO::getOfferInfo)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
