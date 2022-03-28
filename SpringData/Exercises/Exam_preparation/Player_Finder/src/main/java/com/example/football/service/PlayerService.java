package com.example.football.service;



//ToDo - Implement all methods

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface PlayerService {
    boolean areImported();

    String readPlayersFileContent() throws IOException;

    String importPlayers() throws IOException, JAXBException;

    String exportBestPlayers();
}
