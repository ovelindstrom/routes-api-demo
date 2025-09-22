package se.codecadence.routes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBusRequestDTO(

        @NotBlank(message = "Name can not be null or empty.") 
        @Size(min = 6, max = 256, message = "Name must be between 6 and 256 characters.") 
        String name,
        
        @NotNull(message = "Capacity can not be null.")
        @Min(value = 16, message = "Capacity must be at least 16.") 
        @Max(value = 150, message = "Capacity must be at most 150.") 
        Integer capacity

) {
}
