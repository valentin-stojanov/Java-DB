package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.Passenger;
import softuni.exam.models.Plane;
import softuni.exam.models.Ticket;
import softuni.exam.models.Town;
import softuni.exam.models.dto.TicketImportDTO;
import softuni.exam.models.dto.TicketsImportDTO;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
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
public class TicketServiceImpl implements TicketService {
    public static final Path TICKETS_FILE_PATH = Path.of("src/main/resources/files/xml/tickets.xml");

    private final TicketRepository ticketRepository;

    private final ModelMapper modelMapper;
    private final JAXBContext jaxbContext;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;
    private final PlaneRepository planeRepository;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             ModelMapper modelMapper,
                             ValidationUtil validationUtil,
                             TownRepository townRepository,
                             PassengerRepository passengerRepository,
                             PlaneRepository planeRepository) throws JAXBException {
        this.ticketRepository = ticketRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.planeRepository = planeRepository;

        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        jaxbContext = JAXBContext.newInstance(TicketsImportDTO.class);
    }

    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        BufferedReader xmlReader = Files.newBufferedReader(TICKETS_FILE_PATH);
        return xmlReader
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String importTickets() throws IOException, JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        BufferedReader xmlReader = Files.newBufferedReader(TICKETS_FILE_PATH);

        TicketsImportDTO ticketsImportDTO = (TicketsImportDTO) unmarshaller.unmarshal(xmlReader);

        List<TicketImportDTO> ticketImportDTOS = ticketsImportDTO.getTickets();

        StringBuilder result = new StringBuilder();
        for (TicketImportDTO ticketImportDTO : ticketImportDTOS) {
            boolean isValidDTO = this.validationUtil.isValid(ticketImportDTO);
            if (!isValidDTO){
                result
                        .append("Invalid ticket")
                        .append(System.lineSeparator());
                continue;
            }
            Ticket ticket = this.modelMapper.map(ticketImportDTO, Ticket.class);

            String fromTownName = ticketImportDTO.getFromTown().getName();
            String toTownName = ticketImportDTO.getToTown().getName();
            String passengerEmail = ticketImportDTO.getEmail().getEmail();
            String planeRegisterNumber = ticketImportDTO.getPlane().getRegisterNumber();

            Town fromTown = this.townRepository.findByName(fromTownName).get();
            Town toTown = this.townRepository.findByName(toTownName).get();
            Passenger passenger = this.passengerRepository.findByEmail(passengerEmail).get();
            Plane plane = this.planeRepository.findByRegisterNumber(planeRegisterNumber).get();

            ticket.setFromTown(fromTown);
            ticket.setToTown(toTown);
            ticket.setPassenger(passenger);
            ticket.setPlane(plane);

            this.ticketRepository.save(ticket);
            result
                    .append(String.format("Successfully imported Ticket %s - %s", fromTownName, toTownName))
                    .append(System.lineSeparator());
        }
        return result.toString();
    }
}
