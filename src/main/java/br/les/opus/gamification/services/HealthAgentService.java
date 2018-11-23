package br.les.opus.gamification.services;


import br.les.opus.gamification.repositories.HealthAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthAgentService {

    @Autowired
    private HealthAgentRepository healthAgentRepository;


}


