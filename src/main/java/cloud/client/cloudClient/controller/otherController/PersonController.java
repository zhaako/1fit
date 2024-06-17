package cloud.client.cloudClient.controller.otherController;

import cloud.client.cloudClient.jwt.JwtService;
import cloud.client.cloudClient.model.User;
import cloud.client.cloudClient.service.impl.AsyncService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Validated
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private AsyncService asyncService;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<String> valid(@Valid @RequestBody User user) {
        return ResponseEntity.ok("valid");
    }

    @GetMapping("{id}")
    public ResponseEntity<String> getById(
            @PathVariable("id") @Min(0) int personId
    ) {
        return ResponseEntity.ok("valid");
    }

    @GetMapping
    public ResponseEntity<String> getByName(
            @RequestParam("name") @NotBlank String name
    ) {
        return ResponseEntity.ok("valid");
    }
    @GetMapping("/async")
    public CompletableFuture<String> asyncEndpoint() {
        return asyncService.asyncMethod();
    }
    @GetMapping("/async-task")
    public String triggerAsyncTask(@AuthenticationPrincipal User userDetails) {
        asyncService.asyncMethod();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        String u = jwtService.extractUsername(jwtService.generateToken(userDetails));
        String v = jwtService.extractName(jwtService.generateToken(userDetails));
        System.out.println(currentUsername + " " + u + " " + v);
        return "Async task triggered!";
    }
}