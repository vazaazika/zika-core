package br.les.opus.auth.core.services;


import br.les.opus.auth.core.domain.Device;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;


    public Device insertDevice(User user, String token)  {

        Device device = new Device(token, new Date(), user);
        device = deviceRepository.save(device);
        return device;
    }

}
