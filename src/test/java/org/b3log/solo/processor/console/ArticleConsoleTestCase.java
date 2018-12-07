/*
 * Solo - A small and beautiful blogging system written in Java.
 * Copyright (c) 2010-2018, b3log.org & hacpai.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.b3log.solo.processor.console;

import org.apache.commons.lang.StringUtils;
import org.b3log.latke.Keys;
import org.b3log.latke.repository.Query;
import org.b3log.solo.AbstractTestCase;
import org.b3log.solo.MockHttpServletRequest;
import org.b3log.solo.MockHttpServletResponse;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * {@link ArticleConsole} test case.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.0, Dec 7, 2018
 * @since 2.9.7
 */
@Test(suiteName = "processor")
public class ArticleConsoleTestCase extends AbstractTestCase {

    /**
     * Init.
     *
     * @throws Exception exception
     */
    @Test
    public void init() throws Exception {
        super.init();
    }

    /**
     * getArticleThumbs.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void getArticleThumbs() throws Exception {
        final MockHttpServletRequest request = mockRequest();
        request.setRequestURI("/console/thumbs");
        mockAdminLogin(request);
        final MockHttpServletResponse response = mockResponse();

        mockDispatcherServletService(request, response);

        final String content = response.body();
        Assert.assertTrue(StringUtils.contains(content, "{\"sc\":true,"));
    }

    /**
     * markdown2HTML.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void markdown2HTML() throws Exception {
        final MockHttpServletRequest request = mockRequest();
        request.setRequestURI("/console/markdown/2html");
        request.putParameter("markdownText", "**Solo**");
        request.setMethod("POST");
        mockAdminLogin(request);
        final MockHttpServletResponse response = mockResponse();

        mockDispatcherServletService(request, response);

        final String content = response.body();
        Assert.assertTrue(StringUtils.contains(content, "<p><strong>Solo<\\/strong><\\/p>"));
    }

    /**
     * getArticle.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void getArticle() throws Exception {
        final JSONObject article = getArticleRepository().get(new Query()).optJSONArray(Keys.RESULTS).optJSONObject(0);
        final String articleId = article.optString(Keys.OBJECT_ID);

        final MockHttpServletRequest request = mockRequest();
        request.setRequestURI("/console/article/" + articleId);
        mockAdminLogin(request);
        final MockHttpServletResponse response = mockResponse();

        mockDispatcherServletService(request, response);

        final String content = response.body();
        Assert.assertTrue(StringUtils.contains(content, "{\"sc\":true,"));
    }

    /**
     * getArticles.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void getArticles() throws Exception {
        final MockHttpServletRequest request = mockRequest();
        request.setRequestURI("/console/articles/status/published/1/10/20");
        mockAdminLogin(request);
        final MockHttpServletResponse response = mockResponse();

        mockDispatcherServletService(request, response);

        final String content = response.body();
        Assert.assertTrue(StringUtils.contains(content, "{\"sc\":true,"));
    }

    /**
     * removeArticle.
     *
     * @throws Exception exception
     */
    @Test(dependsOnMethods = "init")
    public void removeArticle() throws Exception {
        final JSONObject article = getArticleRepository().get(new Query()).optJSONArray(Keys.RESULTS).optJSONObject(0);
        final String articleId = article.optString(Keys.OBJECT_ID);

        final MockHttpServletRequest request = mockRequest();
        request.setRequestURI("/console/article/" + articleId);
        request.setMethod("DELETE");
        mockAdminLogin(request);
        final MockHttpServletResponse response = mockResponse();

        mockDispatcherServletService(request, response);

        final String content = response.body();
        Assert.assertTrue(StringUtils.contains(content, "{\"sc\":true,"));
    }
}
