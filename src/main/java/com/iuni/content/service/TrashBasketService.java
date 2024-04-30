package com.iuni.content.service;

import com.iuni.content.domain.Task;

import com.iuni.content.helper.join.JoinTrash;
import com.iuni.content.repository.TrashBasketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service("trashBasketService")
public class TrashBasketService {
    private final TrashBasketRepository trashBasketRepository;

    public TrashBasketService(TrashBasketRepository trashBasketRepository){
        this.trashBasketRepository = trashBasketRepository;
    }

    public ArrayList<Task> load(Long memberId){
        try{
            ArrayList<JoinTrash> dataList = this.trashBasketRepository.findAllTrashByMemberId(memberId);
            ArrayList<Task> result = new ArrayList<>();

            for(JoinTrash data : dataList){
                System.out.println(data.getStartDate().orElse(null));
                Task task = new Task();
                task.setId(data.getId());
                task.setMapId(data.getMapId());
                task.setStartDate(data.getStartDate().orElse(null));
                task.setCreateDate(data.getCreateDate().orElse(null));
                task.setDescription(data.getDescription());
                task.setDueDate(data.getDueDate().orElse(null));
                task.setCreatorNickName(data.getCreatorNickName());
                task.setEditorNickName(data.getEditorNickName());
                task.setEndDate(data.getEndDate().orElse(null));
                task.setMajorVersion(data.getMajorVersion());
                task.setMinorVersion(data.getMinorVersion());
                task.setName(data.getName());
                task.setStatusId(data.getStatusId());
                task.setPoint(data.getPoint());
                task.setIsShared(data.getIsShared());

                result.add(task);
            }
            return result;
        }
        catch(Exception e){
            throw e;
        }
    }

}

