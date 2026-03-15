package com.example.myapplication.algorithms.service;

import com.example.myapplication.covid.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TreeQueryService {

    public List<Integer> getAnswersToQueries(List<Integer> par, List<List<Integer>> query) {
        int n = par.size();
        if (n == 0) {
            throw new BadRequestException("par must contain at least one node");
        }

        List<List<Integer>> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            children.add(new ArrayList<>());
        }

        int rootCount = 0;
        for (int i = 0; i < n; i++) {
            Integer parent = par.get(i);
            if (parent == null) {
                throw new BadRequestException("par contains null at index " + i);
            }

            if (parent == -1) {
                rootCount++;
                continue;
            }

            if (parent < 1 || parent > n) {
                throw new BadRequestException("Invalid parent value " + parent + " at index " + i);
            }

            children.get(parent - 1).add(i + 1);
        }

        if (rootCount != 1 || par.get(0) != -1) {
            throw new BadRequestException("Expected exactly one root at node 1 with parent -1");
        }

        for (List<Integer> childList : children) {
            Collections.sort(childList);
        }

        List<Integer> preorder = new ArrayList<>();
        int[] inTime = new int[n + 1];
        int[] subtreeSize = new int[n + 1];

        dfs(1, children, preorder, inTime, subtreeSize);

        if (preorder.size() != n) {
            throw new BadRequestException("Input does not represent a valid tree rooted at node 1");
        }

        List<Integer> result = new ArrayList<>();
        for (List<Integer> q : query) {
            if (q.size() != 2) {
                throw new BadRequestException("Each query must contain exactly 2 integers: [start, index]");
            }

            int start = q.get(0);
            int index = q.get(1);

            if (start < 1 || start > n || index < 1) {
                result.add(-1);
                continue;
            }

            if (index > subtreeSize[start]) {
                result.add(-1);
            } else {
                int pos = inTime[start] + index - 1;
                result.add(preorder.get(pos));
            }
        }

        return result;
    }

    private void dfs(int node, List<List<Integer>> children, List<Integer> preorder, int[] inTime, int[] subtreeSize) {
        inTime[node] = preorder.size();
        preorder.add(node);
        subtreeSize[node] = 1;

        for (int child : children.get(node - 1)) {
            dfs(child, children, preorder, inTime, subtreeSize);
            subtreeSize[node] += subtreeSize[child];
        }
    }
}
