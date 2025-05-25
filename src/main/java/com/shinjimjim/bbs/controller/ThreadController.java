package com.shinjimjim.bbs.controller;
//コントローラーは、ブラウザからのリクエスト（例: URLアクセス）を受け取り、必要な処理をしてレスポンスを返すクラスです。
import com.shinjimjim.bbs.model.Thread;
import com.shinjimjim.bbs.repository.ThreadRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller //これはSpringに「このクラスはWebリクエストを処理するコントローラーです」と伝えるアノテーションです。
public class ThreadController {

    private final ThreadRepository threadRepository; //ThreadRepository は、スレッドの一覧取得や保存を行うデータベースアクセス用インタフェースです。
    //Spring Bootは自動的にこのリポジトリのインスタンスを生成して注入（DI）してくれます。

    public ThreadController(ThreadRepository threadRepository) {
        this.threadRepository = threadRepository;
    }

    @GetMapping("/") //トップページ（スレッド一覧）/ にアクセスすると呼ばれます。
    public String listThreads(Model model) {
        model.addAttribute("threads", threadRepository.findAll()); //threadRepository.findAll() でスレッド一覧を取得。Modelにthreadsという名前でデータを渡します。
        return "thread_list"; //thread_list.html（Thymeleafテンプレート）を表示します。
    }

    @GetMapping("/threads/new") //新規スレッド作成画面
    public String newThreadForm(Model model) {
        model.addAttribute("thread", new Thread()); ///threads/new にアクセスすると「スレッド作成画面（HTMLフォーム）」が表示されます。
        //新しい空の Thread オブジェクトをHTMLに渡して、Thymeleafのフォームバインディングを可能にしています。
        return "thread_form";
    }

    @PostMapping("/threads") //スレッド作成処理　HTMLフォームからPOSTされるとこのメソッドが呼ばれます。
    public String createThread(@ModelAttribute Thread thread) { //@ModelAttribute を使って、HTMLフォームの内容を Thread オブジェクトにマッピング。
        threadRepository.save(thread); //threadRepository.save(thread) でDBに保存。
        return "redirect:/"; //「トップページにリダイレクト」する命令です（リロードによる二重投稿を防ぐ目的もある）。
    }
}
