package br.com.fiap.fiaptechchallengefase4.presenters.controllers;

import br.com.fiap.fiaptechchallengefase4.core.domain.Produto;
import br.com.fiap.fiaptechchallengefase4.presenters.dtos.ProdutoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.fiap.fiaptechchallengefase4.core.usecases.ProdutoUseCase;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoUseCase produtoUseCase;
    @GetMapping
    public List<ProdutoDTO> getAllProdutos() {
        return produtoUseCase.getAllProdutos().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProdutoDTO getProdutoById(@PathVariable Long id) {
        Produto produto = produtoUseCase.getProdutoById(id);
        return convertToDTO(produto);
    }

    @PostMapping
    public void createProduto(@RequestBody ProdutoDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        produtoUseCase.saveProduto(produto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduto(@PathVariable Long id) {
        produtoUseCase.deleteProduto(id);
    }

    private ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        return produtoDTO;
    }

    private Produto convertToEntity(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setId(produtoDTO.getId());
        produto.setNome(produtoDTO.getNome());
        produto.setPreco(produtoDTO.getPreco());
        return produto;
    }
}
