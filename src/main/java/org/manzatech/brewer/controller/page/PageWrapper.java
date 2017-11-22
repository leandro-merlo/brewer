package org.manzatech.brewer.controller.page;

import org.springframework.data.domain.Page;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;

public class PageWrapper<T> {

    private Page<T> page;
    private UriComponentsBuilder uriComponentsBuilder;

    public PageWrapper(Page<T> page, HttpServletRequest request) {
        this.page = page;
        this.uriComponentsBuilder = ServletUriComponentsBuilder.fromRequest(request);
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public List<T> getContent(){
        return this.page.getContent();
    }

    public int getNumber(){
        return this.page.getNumber();
    }

    public int getTotalPages(){
        return this.page.getTotalPages();
    }

    public boolean isFirst(){
        return this.page.isFirst();
    }

    public boolean isLast() {
        return this.page.isLast();
    }

    public boolean isEmpty(){
        return this.getContent().isEmpty();
    }

    public String urlForPage(int page) {
        /*
         * É necessário dar o build(true) e o encode para que números e caractéres especiais consigam
         * ser traduzidos novamente para a página.
         */
        return uriComponentsBuilder.replaceQueryParam("page", page).build(true).encode(Charset.forName("UTF-8")).toUriString();
    }
}
