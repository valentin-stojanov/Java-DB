package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellerDTO;
import softuni.exam.models.dto.SellersImportDTO;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
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
public class SellerServiceImpl implements SellerService {
    public static final Path SELLERS_FILE_PATH = Path.of("src/main/resources/files/xml/sellers.xml");

    private final SellerRepository sellerRepository;
    private final ValidationUtil validationUtil;
    private final JAXBContext jaxbContext;
    private final ModelMapper modelMapper;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository,
                             ValidationUtil validationUtil,
                             ModelMapper modelMapper) throws JAXBException {
        this.sellerRepository = sellerRepository;

        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.jaxbContext = JAXBContext.newInstance(SellersImportDTO.class);
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        BufferedReader reader = Files.newBufferedReader(SELLERS_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        BufferedReader xmlReader = Files.newBufferedReader(SELLERS_FILE_PATH);
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();

        SellersImportDTO sellersImportDTO = (SellersImportDTO) unmarshaller.unmarshal(xmlReader);

        List<SellerDTO> sellerDTOList = sellersImportDTO.getSellers();

        StringBuilder result = new StringBuilder();
        for (SellerDTO sellerDTO : sellerDTOList) {
            boolean isValidDTO = this.validationUtil.isValid(sellerDTO);
            if (!isValidDTO){
                result.append("Invalid seller\n");
                continue;
            }
            Seller seller = this.modelMapper.map(sellerDTO, Seller.class);
            this.sellerRepository.save(seller);
            result.append(String.format("Successfully import seller %s - %s\n",
                    seller.getFirstName(), seller.getEmail()));
        }
        return result.toString();
    }
}
