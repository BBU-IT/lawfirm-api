package _bbu.lawfirmapi.utils;

import _bbu.lawfirmapi.exceptions.NotFoundException;

import org.springframework.stereotype.Component;

@Component
public  class ClassHelper {

    public void isInvalidPage(Integer totalPages , Integer requestedPage){
        if(requestedPage > totalPages){
            throw  new NotFoundException("Page number : " + requestedPage +  " doesn't exist");
        }
    }

}
