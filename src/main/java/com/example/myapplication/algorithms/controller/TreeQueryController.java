package com.example.myapplication.algorithms.controller;

import com.example.myapplication.algorithms.dto.TreeQueryRequest;
import com.example.myapplication.algorithms.dto.TreeQueryResponse;
import com.example.myapplication.algorithms.service.TreeQueryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/algorithms/tree-queries")
public class TreeQueryController {

    private final TreeQueryService treeQueryService;

    public TreeQueryController(TreeQueryService treeQueryService) {
        this.treeQueryService = treeQueryService;
    }

    @PostMapping("/solve")
    @ResponseStatus(HttpStatus.OK)
    public TreeQueryResponse solve(@Valid @RequestBody TreeQueryRequest request) {
        return new TreeQueryResponse(treeQueryService.getAnswersToQueries(request.par(), request.query()));
    }
}
