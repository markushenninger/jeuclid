package cViewer;

/*
 * Copyright 2009 Erhard Kuenzel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Takes a list of Strings as options to a MyComboDialog. The ComboDialogs
 * writes the resultString.
 * 
 * @version $Revision$
 */
public class TransferObject {

    private String result;

    private String comment;

    private final String[] options;

    public TransferObject(final String[] opts) {
        this.options = opts;
    }

    /**
     * Getter method for content.
     * 
     * @return the content
     */
    public String getResult() {
        return this.result;
    }

    /**
     * Setter method for content.
     * 
     * @param content
     *            the content to set
     */
    public void setResult(final String content) {
        this.result = content;
    }

    /**
     * Getter method for options.
     * 
     * @return the options
     */
    public String[] getOptions() {
        return this.options;
    }

    /**
     * Getter method for comment.
     * 
     * @return the comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Setter method for comment.
     * 
     * @param comment
     *            the comment to set
     */
    public void setComment(final String comment) {
        this.comment = comment;
    }

}