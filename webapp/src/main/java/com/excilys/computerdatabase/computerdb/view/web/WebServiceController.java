package com.excilys.computerdatabase.computerdb.view.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.computerdb.model.dto.ComputerDTO;
import com.excilys.computerdatabase.computerdb.model.dto.PageListComputerDTO;
import com.excilys.computerdatabase.computerdb.service.ComputerService;

@RestController
@RequestMapping(value = "/computer")
public class WebServiceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceController.class);
    @Autowired
    ComputerService computerService;

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ComputerDTO getComputer(@PathVariable Long id) {
        return computerService.getComputerDTOById(id);
    }

    @RequestMapping(value = "/list/page/{number}/size/{s}/search/{pattern}", method = RequestMethod.GET)
    @ResponseBody
    public PageListComputerDTO getPage(@PathVariable Long number, @PathVariable Long s, @PathVariable String pattern) {

        return computerService.getComputerDTOList(pattern, s, number, "name");

    }

    @RequestMapping(value = "/edit/{id}",method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable Long id, @RequestBody ComputerDTO computerDTO) {
        
        LOGGER.info(computerDTO.toString());

        return ResponseEntity.noContent().build();

    }

//    @RequestMapping(method = RequestMethod.POST)
//    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Bookmark input) {
//        this.validateUser(userId);
//
//        return this.accountRepository.findByUsername(userId).map(account -> {
//            Bookmark result = bookmarkRepository.save(new Bookmark(account, input.uri, input.description));
//
//            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId())
//                    .toUri();
//
//            return ResponseEntity.created(location).build();
//        }).orElse(ResponseEntity.noContent().build());
//
//    }

}
