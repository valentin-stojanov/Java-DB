package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Passenger;
import softuni.exam.models.Town;
import softuni.exam.models.dto.PassengerImportDTO;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {
    public static final Path PASSENGER_FILE_PATH = Path.of("src/main/resources/files/json/passengers.json");

    private final PassengerRepository passengerRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                Gson gson,
                                ValidationUtil validationUtil,
                                ModelMapper modelMapper,
                                TownRepository townRepository) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;

        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        BufferedReader reader = Files.newBufferedReader(PASSENGER_FILE_PATH);
        return reader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importPassengers() throws IOException {
        PassengerImportDTO[] passengerImportDTOS = this.gson.fromJson(readPassengersFileContent(), PassengerImportDTO[].class);

        StringBuilder result = new StringBuilder();
        for (PassengerImportDTO passengerImportDTO : passengerImportDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(passengerImportDTO);
            if (!isValidDTO){
                result
                        .append("Invalid passenger")
                        .append(System.lineSeparator());
                continue;
            }
            String townName = passengerImportDTO.getTown();

            Optional<Town> optionalTown = this.townRepository.findByName(townName);
            Passenger passenger = this.modelMapper.map(passengerImportDTO, Passenger.class);

            passenger.setTown(optionalTown.get());
            this.passengerRepository.save(passenger);
            result
                    .append(String.format("Successfully imported Passenger %s - %s", passenger.getFirstName(), passenger.getEmail()))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        return null;
    }
}
