package com.project.quizo.Web.Rest.Controller;

import com.project.quizo.Resource.UserDTO;
import com.project.quizo.Resource.UserLoginPostDTO;
import com.project.quizo.Resource.UserRegisterDTO;
import com.project.quizo.Service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Controller
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegisterDTO userRegisterDTO() {
        return new UserRegisterDTO();
    }

    @GetMapping("/register")
    public String showRegistration(Model model) {
        return "registration";
    }

    @GetMapping("/login")
    public String login(Model model) { return "login"; }

    // Get user
    @GetMapping("/{id}")
    @ApiResponses(value = {@ApiResponse(code = 404, message = "User not found.")})
    public ResponseEntity<UserDTO> getUser(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(new UserDTO(userService.findById(id)));
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid UserRegisterDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        userService.registerUser(user);

        return "redirect:/login?successRegister";
    }

}