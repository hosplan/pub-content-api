package com.iuni.content.service;

import com.iuni.content.domain.Status;
import com.iuni.content.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("statusService")
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository){
        this.statusRepository = statusRepository;
    }

    public Status get(Long id){
        try{
            return this.statusRepository.findById(id).orElse(new Status());
        }
        catch(Exception e){
            throw e;
        }
    }

    public List<Status> loadByType(String type){
        try{
            return this.statusRepository.findByType(type).orElse(new ArrayList<Status>());
        }
        catch(Exception e){
            throw e;
        }
    }

    public List<Status> load(){
        try{
            return this.statusRepository.findAll();
        }
        catch(Exception e){
            throw e;
        }
    }

    public Status create(Status data){
        try{
            return this.statusRepository.save(data);
        }
        catch(Exception e){
            throw e;
        }
    }

    public Boolean update(Status data){
        try{
            Optional<Status> opt_updateStatus = this.statusRepository.findById(data.getId());
            if(opt_updateStatus.isPresent()){
                Status updateStatus = opt_updateStatus.get();
                updateStatus.setName(data.getName());
                updateStatus.setDescription(data.getDescription());
                updateStatus.setType(data.getType());
                return true;
            } else {
                return false;
            }
        }
        catch(Exception e){
            throw e;
        }
    }

    public Boolean remove(Long id){
        try{
            this.statusRepository.deleteById(id);
            return true;
        }
        catch(Exception e){
            throw e;
        }
    }
}
