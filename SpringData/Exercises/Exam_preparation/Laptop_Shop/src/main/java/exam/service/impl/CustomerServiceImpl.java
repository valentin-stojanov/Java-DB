package exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exam.model.dtos.ImportCustomerDTO;
import exam.model.entities.Customer;
import exam.model.entities.Town;
import exam.repository.CustomerRepository;
import exam.repository.TownRepository;
import exam.service.CustomerService;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Validator validator;
    private final Path JSON_CUSTOMER_PATH = Path.of("src/main/resources/files/json/customers.json");
    private final TownRepository townRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, TownRepository townRepository) {
        this.customerRepository = customerRepository;
        this.townRepository = townRepository;

        this.modelMapper = new ModelMapper();
        this.modelMapper.addConverter(ctx -> LocalDate.parse(ctx.getSource(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                String.class, LocalDate.class);
        this.gson = new GsonBuilder().create();
        this.validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public boolean areImported() {
        return this.customerRepository.count() > 0;
    }

    @Override
    public String readCustomersFileContent() throws IOException {
        BufferedReader jsoReader = Files.newBufferedReader(JSON_CUSTOMER_PATH);
        return jsoReader.lines().collect(Collectors.joining("\n"));
    }

    @Override
    public String importCustomers() throws IOException {
        ImportCustomerDTO[] importCustomerDTOS = this.gson.fromJson(readCustomersFileContent(), ImportCustomerDTO[].class);

        StringBuilder result = new StringBuilder();
        for (ImportCustomerDTO importCustomerDTO : importCustomerDTOS) {
            Set<ConstraintViolation<ImportCustomerDTO>> violationSet = validator.validate(importCustomerDTO);

            if (violationSet.isEmpty()){
                Optional<Customer> optionalCustomer = this.customerRepository.findByEmail(importCustomerDTO.getEmail());

                if (optionalCustomer.isEmpty()){
                    Customer customer = this.modelMapper.map(importCustomerDTO, Customer.class);

                    Optional<Town> optionalTown = this.townRepository.findByName(importCustomerDTO.getTown().getName());

                    customer.setTown(optionalTown.get());

                    result.append(String.format("Successfully imported Customer %s %s - %s\n",
                            customer.getFirstName(), customer.getLastName(), customer.getEmail()));
                } else {
                    result.append("Invalid Customer\n");
                }
            } else {
                result.append("Invalid Customer\n");
            }
        }
        return result.toString();
    }
}
