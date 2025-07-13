package com.victorvivas.pruebaaccenture.accenture_franquicia_api.entrypoints.dto;

import java.util.List;

public class FranchiseDTO {
    private String name;
    private List<BranchDTO> branches;

    public FranchiseDTO() {}

    public FranchiseDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BranchDTO> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDTO> branches) {
        this.branches = branches;
    }
}
