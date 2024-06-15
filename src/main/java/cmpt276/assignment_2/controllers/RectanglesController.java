package cmpt276.assignment_2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cmpt276.assignment_2.models.Rectangle;
import cmpt276.assignment_2.models.RectangleRepository;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RectanglesController {
    
    @Autowired
    private RectangleRepository rectangleRepo;

    @GetMapping("/rectangles/view")
    public String getAllRectangles(Model model) {

        List<Rectangle> rectangles = rectangleRepo.findAll();

        model.addAttribute("us", rectangles);

        return "rectangles/showAll";

    }

    @PostMapping("/rectangles/add")
    public String addRectangle(@RequestParam Map<String, String> newRectangle, HttpServletResponse response) {

        String newName = newRectangle.get("name");
        int newWidth = Integer.parseInt(newRectangle.get("width"));
        int newHeight = Integer.parseInt(newRectangle.get("height"));
        String newColor = newRectangle.get("color");

        rectangleRepo.save(new Rectangle(newName, newWidth, newHeight, newColor));

        response.setStatus(201);

        return "redirect:/rectangles/view";

    }

    
    
}