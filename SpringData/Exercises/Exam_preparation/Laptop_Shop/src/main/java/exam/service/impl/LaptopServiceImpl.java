package exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.BestLaptopDTO;
import exam.model.dtos.ImportLaptopDTO;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.repository.LaptopRepository;
import exam.repository.ShopRepository;
import exam.service.LaptopService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LaptopServiceImpl implements LaptopService {
    private final LaptopRepository laptopRepository;
    private final Path LAPTOP_JSON_PATH = Path.of("src/main/resources/files/json/laptops.json");
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Validator validator;
    private final ShopRepository shopRepository;

    @Autowired
    public LaptopServiceImpl(LaptopRepository laptopRepository, ShopRepository shopRespository) {
        this.laptopRepository = laptopRepository;
        this.shopRepository = shopRespository;

        this.modelMapper = new ModelMapper();
        this.gson = new GsonBuilder().create();
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public boolean areImported() {
        return this.laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        BufferedReader jsonReader = Files.newBufferedReader(LAPTOP_JSON_PATH);
        return jsonReader.lines().collect(Collectors.joining("\n"));
    }

    @Override
    public String importLaptops() throws IOException {
        ImportLaptopDTO[] importLaptopDTOS = this.gson.fromJson(readLaptopsFileContent(), ImportLaptopDTO[].class);

        StringBuilder result = new StringBuilder();
        for (ImportLaptopDTO importLaptopDTO : importLaptopDTOS) {
            Set<ConstraintViolation<ImportLaptopDTO>> violationSet = validator.validate(importLaptopDTO);

            if (violationSet.isEmpty()){
                Optional<Laptop> optionalLaptop = this.laptopRepository.findByMacAddress(importLaptopDTO.getMacAddress());
                if (optionalLaptop.isEmpty()){
                    Optional<Shop> optionalShop = this.shopRepository.findByName(importLaptopDTO.getShop().getName());

                    Laptop laptop = this.modelMapper.map(importLaptopDTO, Laptop.class);
                    laptop.setShop(optionalShop.get());

                    this.laptopRepository.save(laptop);
                    result.append(String.format("Successfully imported Laptop %s - %.2f - %d - %d\n",
                            laptop.getMacAddress(), laptop.getCpuSpeed(), laptop.getRam(), laptop.getStorage()));
                } else {
                    result.append("Invalid Laptop\n");
                }
            } else {
                result.append("Invalid Laptop\n");
            }
        }
        return result.toString();
    }

    @Override
    public String exportBestLaptops() {
        List<BestLaptopDTO> theBestLaptop = this.laptopRepository.findTheBestLaptop();
        String collect = theBestLaptop
                .stream()
                .map(e -> e.getLaptopInfo())
                .collect(Collectors.joining("\n"));
        return collect;
    }
}
