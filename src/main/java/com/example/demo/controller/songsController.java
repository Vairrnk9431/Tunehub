package com.example.demo.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.Songs;
import com.example.demo.services.SongsService;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class songsController {

	@Autowired
	SongsService songsrev;

	@PostMapping("/addsongs")
	public String addSongs(@ModelAttribute Songs song) {

		boolean status = songsrev.songExists(song.getName());
		if (status == false) {
			songsrev.addSongs(song);
			return "songsuccess";

		} else {
			return "songfail";

		}

	}
	@GetMapping("/map-viewsongs")
	public String viewSongs(Model model) {
		List <Songs> songslist =songsrev.fetchAllSongs();
		model.addAttribute("songslist", songslist);
		return "displaysongs";
	}

	@GetMapping("/viewsongs")
	public String viewCustomerSongs(Model model) {
		
		boolean primeCustomerStatus=true;
		if(primeCustomerStatus==true)
		{
			List <Songs> songslist =songsrev.fetchAllSongs();
			model.addAttribute("songslist", songslist);
			return "displaysongs";
			
		}
		else 
		{
			return "makepayment";
		}
	
	}
	
	
	
}
