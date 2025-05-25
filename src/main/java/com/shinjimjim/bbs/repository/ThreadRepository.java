package com.shinjimjim.bbs.repository;
//データベースとやり取りするための「リポジトリ（Repository）」クラスを作成する。
import com.shinjimjim.bbs.model.Thread;
import org.springframework.data.jpa.repository.JpaRepository; //Spring Data JPA は、リポジトリのコードを ほぼ書かずにDBと連携できる ライブラリです。

public interface ThreadRepository extends JpaRepository<Thread, Long> { //これは 「Threadエンティティ用のリポジトリ」 を意味しています。
    // JpaRepositoryがCRUDメソッドを全部用意してくれます
}
