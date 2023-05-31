/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.FASP.model;

import cc.altius.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author akil
 */
public class ForecastTree<T> {

    public ForecastTree(ForecastNode<T> root) {
        this.root = root;
        this.root.setLevel(0);
        this.root.setSortOrder("00");
        flatList = new LinkedList<>();
        flatList.add(root);
    }

    private final ForecastNode<T> root;
    @JsonView({Views.ReportView.class, Views.InternalView.class})
    private List<ForecastNode<T>> flatList = new LinkedList<>();

    public void addNode(ForecastNode<T> n) throws Exception {
        if (n.getParent() == null) {
            throw new Exception("Must have a valid Parent Id");
        }
        ForecastNode<T> checkNode = new ForecastNode<>(n.getParent(), 0);
        int idx = this.flatList.indexOf(checkNode);
        if (idx == -1) {
            throw new Exception("Must have a valid Parent Id");
        }
        ForecastNode<T> foundNode = findNode(this.root, n.getParent());
        if (foundNode != null) {
            n.setLevel(foundNode.getLevel() + 1);
            String newSortOrder = foundNode.getSortOrder() + ".";
            newSortOrder += StringUtils.pad(Integer.toString(foundNode.getNoOfChild() + 1), '0', 2, StringUtils.LEFT);
            n.setSortOrder(newSortOrder);
            foundNode.getTree().add(n);
        } else {
            n.setLevel(this.root.getLevel() + 1);
            String newSortOrder = this.root.getSortOrder() + ".";
            newSortOrder += StringUtils.pad(Integer.toString(this.root.getNoOfChild() + 1), '0', 2, StringUtils.LEFT);
            n.setSortOrder(newSortOrder);
            this.root.getTree().add(n);
        }
        this.flatList.add(n);
    }

    public ForecastNode<T> findNode(int id) {
        ForecastNode<T> n = this.root;
        return findNode(n, id);
    }

    private ForecastNode<T> findNode(ForecastNode<T> node, int id) {
        if (node.getId() == id) {
            return node;
        }
        for (ForecastNode<T> n : node.getTree()) {
            if (n.getId() == id) {
                return n;
            } else if (!n.getTree().isEmpty()) {
                ForecastNode foundNode = findNode(n, id);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;
    }

    public ForecastNode<T> findNodeByPayloadId(int payloadId) {
        ForecastNode<T> n = this.root;
        return findNodeByPayloadId(n, payloadId);
    }

    private ForecastNode<T> findNodeByPayloadId(ForecastNode<T> node, int payloadId) {
        if (node.getPayloadId() == payloadId) {
            return node;
        }
        for (ForecastNode<T> n : node.getTree()) {
            if (n.getPayloadId() == payloadId) {
                return n;
            } else if (!n.getTree().isEmpty()) {
                ForecastNode foundNode = findNodeByPayloadId(n, payloadId);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;
    }

    @JsonIgnore
    public ForecastNode<T> getTreeRoot() {
        return this.root;
    }

    public List<ForecastNode<T>> getFlatList() {
        return this.flatList;
    }

    @JsonIgnore
    public List<T> getPayloadSubList(int id, boolean includeSelf, int level) {
        List<T> subList = new LinkedList<>();
        ForecastNode<T> n = findNode(id);
        if (includeSelf) {
            subList.add(n.getPayload());
        }
        if (level == -1 || level > 0) {
            n.getTree().forEach((child) -> {
                getPayloadSubList(child, subList, (level > 0 ? level - 1 : level));
            });
        }
        return subList;
    }

    @JsonIgnore
    public List<ForecastNode<T>> getTreeFullList() {
        return getTreeSubList(1, true, -1);
    }

    @JsonIgnore
    public List<ForecastNode<T>> getTreeSubList(int id, boolean includeSelf, int level) {
        List<ForecastNode<T>> subList = new LinkedList<>();
        ForecastNode<T> n = findNode(id);
        if (includeSelf) {
            subList.add(n);
        }
        if (level == -1 || level > 0) {
            n.getTree().forEach((child) -> {
                getTreeSubList(child, subList, (level > 0 ? level - 1 : level));
            });
        }
        return subList;
    }

    private void getPayloadSubList(ForecastNode<T> n, List<T> subList, int level) {
        subList.add(n.getPayload());
        if (level == -1 || level > 0) {
            n.getTree().forEach((child) -> {
                getPayloadSubList(child, subList, (level > 0 ? level - 1 : level));
            });
        }
    }

    private void getTreeSubList(ForecastNode<T> n, List<ForecastNode<T>> subList, int level) {
        subList.add(n);
        if (level == -1 || level > 0) {
            n.getTree().forEach((child) -> {
                getTreeSubList(child, subList, (level > 0 ? level - 1 : level));
            });
        }
    }
}
