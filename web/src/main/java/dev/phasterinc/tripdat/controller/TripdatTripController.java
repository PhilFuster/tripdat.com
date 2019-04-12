package dev.phasterinc.tripdat.controller;

import dev.phasterinc.tripdat.service.TripdatTripItemService;
import dev.phasterinc.tripdat.service.TripdatTripService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class TripdatTripController {



    private TripdatTripService tripdatTripService;

    private TripdatTripItemService tripdatTripItemService;

    // == constructors ==
    @Autowired
    public TripdatTripController(TripdatTripService tripdatTripService, TripdatTripItemService tripdatTripItemService ){
        this.tripdatTripService = tripdatTripService;
        this.tripdatTripItemService = tripdatTripItemService;
    }

    @GetMapping("/trip/")
    public String tripForm(Model model) {
        model.addAttribute("trips", tripdatTripService.findAll());
        model.addAttribute("tripItems", tripdatTripService.getTripItemsByTripId());

        return "trips";

    }



}
