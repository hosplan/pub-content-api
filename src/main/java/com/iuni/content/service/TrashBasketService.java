package com.iuni.content.service;

import com.iuni.content.domain.Task;

import com.iuni.content.domain.TrashBasket;
import com.iuni.content.helper.join.JoinTrash;
import com.iuni.content.repository.TrashBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;


@Service("trashBasketService")
@RequiredArgsConstructor
public class TrashBasketService {
    private final TrashBasketRepository trashBasketRepository;
    private BoardService boardService;
    private TaskService taskService;


    public String getNowDate(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(format);
        return formatDateTime;
    }

    public HashMap<String, String> createTrash(Long id,Integer type){
        HashMap<String, String> result = new HashMap<>();
        try{
            Date now = new Date();
            TrashBasket trash = new TrashBasket();
            trash.setTrashId(id);
            trash.setType(type);
            trash.setCreateDate(now);
            TrashBasket save = trashBasketRepository.save(trash);
            //soutv
            result.put("result", "success");
            return result;
        }
        catch(Exception e){
            result.put("result", "fail");
            return result;
        }
    }


    public HashMap<String, String> remove(Long id){
        HashMap<String, String> result = new HashMap<>();
        try{
            this.trashBasketRepository.deleteById(id);
            result.put("result", "success");
            return result;
        }
        catch(Exception e){
            result.put("result", "fail");
            return result;
        }
    }
    public Optional<TrashBasket> trashBasketCheck(Long id){
        Optional<TrashBasket> optTrash = trashBasketRepository.findById(id);
        return optTrash;
    }
    public HashMap<String, String> restore(TrashBasket data){
        HashMap<String, String> result = new HashMap<>();
        Boolean condition = false;
        try{
            Optional<TrashBasket> optTrash = trashBasketRepository.findById(data.getId());
            if(optTrash.isPresent()){
                switch(optTrash.get().getType()){
                    case 0 :
                        condition = taskService.restore(optTrash.get().getTrashId());
                        break;
                    case 1 :
                        condition = boardService.restore(optTrash.get().getTrashId());
                        break;
                    default:
                        result.put("result", "fail");
                        return result;
                }
                if(condition){
                    result = remove(data.getId());
                    return result;
                }
                else{
                    return result;
                }
            }
            result.put("result", "fail");
            return result;
        }
        catch(Exception e){
            result.put("result", "fail");
            return result;
        }
    }

    public ArrayList<TrashBasket> load(Long memberId){
        try{
            ArrayList<JoinTrash> dataList = this.trashBasketRepository.findAllTrashByMemberId(memberId);
            ArrayList<TrashBasket> result = new ArrayList<>();

            for(JoinTrash data : dataList){
                TrashBasket trashBasket = new TrashBasket();
                trashBasket.setId(data.getId());
                trashBasket.setTrashId(data.getTrashId());
                trashBasket.setName(data.getName());
                trashBasket.setType(data.getType());
                trashBasket.setCreateDate(data.getCreateDate().orElse(null));

                result.add(trashBasket);
            }
            return result;
        }
        catch(Exception e){
            throw e;
        }
    }

}

