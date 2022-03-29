package exam.service.impl;

import exam.model.dtos.ImportShopDTO;
import exam.model.dtos.ImportShopsDTO;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.repository.TownRepository;
import exam.service.ShopService;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final Path SHOP_XML_PATH = Path.of("src/main/resources/files/xml/shops.xml");
    private final JAXBContext jaxbContext;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final TownRepository townRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, TownRepository townRepository) throws JAXBException {
        this.shopRepository = shopRepository;
        this.townRepository = townRepository;

        this.jaxbContext = JAXBContext.newInstance(ImportShopsDTO.class);
        this.modelMapper = new ModelMapper();
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public boolean areImported() {
        return this.shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        BufferedReader xmlReader = Files.newBufferedReader(SHOP_XML_PATH);
        return xmlReader
                .lines()
                .collect(Collectors.joining("\n"));
    }

    @Override
    public String importShops() throws JAXBException, IOException {
        BufferedReader xmlReader = Files.newBufferedReader(SHOP_XML_PATH);
        Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();

        ImportShopsDTO shopsDTO = (ImportShopsDTO) unmarshaller.unmarshal(xmlReader);
        List<ImportShopDTO> shopDTOS = shopsDTO.getShops();

        StringBuilder result = new StringBuilder();
        for (ImportShopDTO shopDTO : shopDTOS) {
            Set<ConstraintViolation<ImportShopDTO>> violationSet = this.validator.validate(shopDTO);
            if (violationSet.isEmpty()){
                Optional<Shop> optionalShop = this.shopRepository.findByName(shopDTO.getName());

                if (optionalShop.isEmpty()){
                    Shop shop = this.modelMapper.map(shopDTO, Shop.class);
                    Optional<Town> optionalTown = this.townRepository.findByName(shopDTO.getTown().getName());
                    shop.setTown(optionalTown.get());

                    this.shopRepository.save(shop);

                    result.append(String.format("Successfully imported Shop %s - %.0f\n",
                            shop.getName(), shop.getIncome()));
                } else {
                    result.append("Invalid shop\n");
                }
            } else {
                result.append("Invalid shop\n");
            }
        }
        return result.toString();
    }
}
