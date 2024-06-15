package cmpt276.assignment_2.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        model.addAttribute("rectangles", rectangles);

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

    @PostMapping("/rectangles/delete")
    public String deleteRectangle(@RequestParam("uid") int uid) {
        if (rectangleRepo.existsById(uid)) {
            rectangleRepo.deleteById(uid);
            return "redirect:/rectangles/view";
        } else {
            return "redirect:/rectangles/view";
        }
    }

    @GetMapping("/rectangles/edit")
    public String editRectangle(@RequestParam("uid") int uid, Model model) {
        Rectangle rectangle = rectangleRepo.findById(uid).orElse(null);
        if (rectangle != null) {
            model.addAttribute("rectangle", rectangle);
            return "rectangles/editRectangle";
        } else {
            return "redirect:/rectangles/view";
        }
    }

    @PostMapping("/rectangles/update")
    public String updateRectangle(@ModelAttribute Rectangle rectangle) {
        rectangleRepo.save(rectangle);
        return "redirect:/rectangles/view";
    }

    
}