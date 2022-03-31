package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferDTO;
import softuni.exam.models.dto.OffersImportDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.Picture;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {
    private static final Path OFFERS_FILE_PATH = Path.of("src/main/resources/files/xml/offers.xml");

    private final OfferRepository offerRepository;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final JAXBContext jaxbContext;

    public OfferServiceImpl(OfferRepository offerRepository,
                            CarRepository carRepository,
                            SellerRepository sellerRepository, ValidationUtil validationUtil, ModelMapper modelMapper) throws JAXBException {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;

        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.jaxbContext = JAXBContext.newInstance(OffersImportDTO.class);
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        BufferedReader reader = Files.newBufferedReader(OFFERS_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        BufferedReader xmlReader = Files.newBufferedReader(OFFERS_FILE_PATH);
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
        OffersImportDTO offersImportDTO = (OffersImportDTO) unmarshaller.unmarshal(xmlReader);

        List<OfferDTO> offerDTOList = offersImportDTO.getOffers();

        StringBuilder result = new StringBuilder();
        for (OfferDTO offerDTO : offerDTOList) {
            boolean isValidDTO = this.validationUtil.isValid(offerDTO);
            if(!isValidDTO){
                result.append("Invalid offer\n");
                continue;
            }
            Offer offer = this.modelMapper.map(offerDTO, Offer.class);

            long carId = offerDTO.getCar().getId();
            long sellerId = offerDTO.getSeller().getId();

            Optional<Car> optionalCar = this.carRepository.findById(carId);
            Optional<Seller> optionalSeller = this.sellerRepository.findById(sellerId);

            Car car = optionalCar.get();
            Set<Picture> carPictures = car.getPictures();
            Seller seller = optionalSeller.get();

            offer.setCar(car);
            offer.setPicture(carPictures);
            offer.setSeller(seller);

            this.offerRepository.save(offer);
            result.append(String.format("Successfully import offer %s - %s\n",
                    offer.getAddedOn(), offer.isHasGoldStatus()));
        }
        return result.toString();
    }
}
