package com.example.lojacarrinho.controller;

import com.example.lojacarrinho.model.Carrinho;
import com.example.lojacarrinho.model.ItemCarrinho;
import com.example.lojacarrinho.model.Produto;
import com.example.lojacarrinho.repository.CarrinhoRepository;
import com.example.lojacarrinho.repository.ItemCarrinhoRepository;
import com.example.lojacarrinho.repository.ProdutoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SistemaController {

    private final ProdutoRepository produtoRepository;
    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;

    public SistemaController(ProdutoRepository produtoRepository, CarrinhoRepository carrinhoRepository, ItemCarrinhoRepository itemCarrinhoRepository) {
        this.produtoRepository = produtoRepository;
        this.carrinhoRepository = carrinhoRepository;
        this.itemCarrinhoRepository = itemCarrinhoRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/produtos")
    public String listarProdutos(Model model) {
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("produto", new Produto());
        return "produtos";
    }

    @PostMapping("/produtos/salvar")
    public String salvarProduto(@ModelAttribute Produto produto) {
        produtoRepository.save(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return "redirect:/produtos";
    }

    @GetMapping("/produtos/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        model.addAttribute("produto", produtoRepository.findById(id).orElse(new Produto()));
        model.addAttribute("produtos", produtoRepository.findAll());
        return "produtos";
    }

    @GetMapping("/carrinhos")
    public String listarCarrinhos(Model model) {
        model.addAttribute("carrinhos", carrinhoRepository.findAll());
        model.addAttribute("carrinho", new Carrinho());
        return "carrinhos";
    }

    @PostMapping("/carrinhos/salvar")
    public String salvarCarrinho(@ModelAttribute Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
        return "redirect:/carrinhos";
    }

    @GetMapping("/carrinhos/deletar/{id}")
    public String deletarCarrinho(@PathVariable Long id) {
        carrinhoRepository.deleteById(id);
        return "redirect:/carrinhos";
    }

    @GetMapping("/carrinhos/editar/{id}")
    public String editarCarrinho(@PathVariable Long id, Model model) {
        model.addAttribute("carrinho", carrinhoRepository.findById(id).orElse(new Carrinho()));
        model.addAttribute("carrinhos", carrinhoRepository.findAll());
        return "carrinhos";
    }

    @GetMapping("/itens")
    public String listarItens(Model model) {
        model.addAttribute("itens", itemCarrinhoRepository.findAll());
        model.addAttribute("itemCarrinho", new ItemCarrinho());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("carrinhos", carrinhoRepository.findAll());
        return "itens";
    }

    @PostMapping("/itens/salvar")
    public String salvarItem(@ModelAttribute ItemCarrinho itemCarrinho) {
        itemCarrinhoRepository.save(itemCarrinho);
        return "redirect:/itens";
    }

    @GetMapping("/itens/deletar/{id}")
    public String deletarItem(@PathVariable Long id) {
        itemCarrinhoRepository.deleteById(id);
        return "redirect:/itens";
    }

    @GetMapping("/itens/editar/{id}")
    public String editarItem(@PathVariable Long id, Model model) {
        model.addAttribute("itemCarrinho", itemCarrinhoRepository.findById(id).orElse(new ItemCarrinho()));
        model.addAttribute("itens", itemCarrinhoRepository.findAll());
        model.addAttribute("produtos", produtoRepository.findAll());
        model.addAttribute("carrinhos", carrinhoRepository.findAll());
        return "itens";
    }

    @GetMapping("/relatorio")
    public String relatorio(Model model) {
        List<Produto> produtos = produtoRepository.findAll();
        List<String> nomes = produtos.stream().map(Produto::getNome).collect(Collectors.toList());
        List<Integer> quantidades = produtos.stream().map(p -> {
            return p.getItens() != null ? p.getItens().stream().mapToInt(ItemCarrinho::getQuantidade).sum() : 0;
        }).collect(Collectors.toList());

        model.addAttribute("nomes", nomes);
        model.addAttribute("quantidades", quantidades);
        return "relatorio";
    }
}