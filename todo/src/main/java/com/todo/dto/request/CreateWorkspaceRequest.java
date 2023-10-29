package com.todo.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateWorkspaceRequest {

        @NotNull
        @NotEmpty
        @Schema(description = "Name of the workspace", example = "New Workspace", required = true)
        private String name;

        public CreateWorkspaceRequest(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
}
