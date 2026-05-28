package com.example.lojacarrinho.repository;
import com.example.lojacarrinho.model.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {}