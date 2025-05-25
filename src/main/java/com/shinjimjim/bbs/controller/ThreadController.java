package com.shinjimjim.bbs.controller;
//コントローラーは、ブラウザからのリクエスト（例: URLアクセス）を受け取り、必要な処理をしてレスポンスを返すクラスです。
import com.shinjimjim.bbs.model.Thread;
import com.shinjimjim.bbs.model.Post;
import com.shinjimjim.bbs.repository.ThreadRepository;
import com.shinjimjim.bbs.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller //これはSpringに「このクラスはWebリクエストを処理するコントローラーです」と伝えるアノテーションです。
public class ThreadController {

    private final ThreadRepository threadRepository; //ThreadRepository は、スレッドの一覧取得や保存を行うデータベースアクセス用インタフェースです。
    //Spring Bootは自動的にこのリポジトリのインスタンスを生成して注入（DI）してくれます。
    private final PostRepository postRepository;

    public ThreadController(ThreadRepository threadRepository, PostRepository postRepository) {
        this.threadRepository = threadRepository;
        this.postRepository = postRepository;
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

    @GetMapping("/threads/{id}") //スレッド詳細表示　/threads/{id} というURLでアクセスしたとき、スレッドの詳細ページを表示。
    public String viewThread(@PathVariable Long id, Model model) { //@PathVariable：URLの{id}を変数として使う
        Thread thread = threadRepository.findById(id).orElseThrow(); // URLのidからスレッドを取得
        model.addAttribute("thread", thread); // スレッドをテンプレートに渡す
        model.addAttribute("posts", postRepository.findByThreadOrderByPostedAtAsc(thread)); // 投稿一覧（昇順）を渡す
        model.addAttribute("post", new Post()); // 新規投稿フォーム用の空オブジェクト
        return "thread_view"; // 表示するHTMLテンプレート名（resources/templates/thread_view.html）
    }

    @PostMapping("/threads/{id}/posts") //投稿処理　投稿フォームから送信された内容を受け取り、データベースに保存する。
    public String addPost(@PathVariable Long id, @ModelAttribute Post post) {
        Thread thread = threadRepository.findById(id).orElseThrow(); // 投稿対象のスレッド取得
        post.setThread(thread); // 投稿にスレッドを紐づける

        // 投稿者名が空なら「名無しさん」にする
        if (post.getUsername() == null || post.getUsername().trim().isEmpty()) {
            post.setUsername("名無しさん");
        }

        postRepository.save(post); // データベースに保存
        return "redirect:/threads/" + id; // 詳細ページへリダイレクト（リロード対策）
    }
}
