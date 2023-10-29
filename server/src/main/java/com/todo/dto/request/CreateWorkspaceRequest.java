package com.todo.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for the CreateWorkspaceRequest.
 * This DTO is used to create a workspace.
 * The name is required.
 * If the name is not set, a default name will be used.
 * If the name is set to an empty string, the name will be removed.
 */
@Hidden
public class CreateWorkspaceRequest {

        /**
         * The name of the workspace.
         */
        @NotNull
        @NotEmpty
        @Schema(description = "Name of the workspace", example = "New Workspace", required = true)
        private String name;

        /**
         * Constructor for the CreateWorkspaceRequest.
         * @param name The name of the workspace.
         */
        public CreateWorkspaceRequest(String name){
            this.name = name;
        }

        /**
         * Get the name of the workspace.
         * @return The name of the workspace.
         */
        public String getName(){
            return name;
        }
}
