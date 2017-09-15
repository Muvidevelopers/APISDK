package com.home.apisdk.apiModel;

import java.util.ArrayList;

/**
 * Created by MUVI on 1/20/2017.
 */

public class GetMenusOutputModel {

    /**
     * This Method is use to Get the Message
     *
     * @return msg
     */

    public String getMsg() {
        return msg;
    }

    /**
     * This Method is use to Set the Message
     *
     * @param msg For Setting The Message
     */

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * This Method is use to Get the Main Menu Model Class
     *
     * @return mainMenuModel
     */

    public ArrayList<MainMenu> getMainMenuModel() {
        return mainMenuModel;
    }

    /**
     * This Method is use to Set the Main Menu Model Class
     *
     * @param mainMenuModel For Setting The Main Menu Model Class
     */

    public void setMainMenuModel(ArrayList<MainMenu> mainMenuModel) {
        this.mainMenuModel = mainMenuModel;
    }

    /**
     * This Method is use to Get the User Menu Model Class
     *
     * @return userMenuModel
     */

    public ArrayList<UserMenu> getUserMenuModel() {
        return userMenuModel;
    }

    /**
     * This Method is use to Set the User Menu Model Class
     *
     * @param userMenuModel For Setting The User Menu Model Class
     */

    public void setUserMenuModel(ArrayList<UserMenu> userMenuModel) {
        this.userMenuModel = userMenuModel;
    }

    /**
     * This Method is use to Get the Footer Menu Model Class
     *
     * @return footerMenuModel
     */

    public ArrayList<FooterMenu> getFooterMenuModel() {
        return footerMenuModel;
    }

    /**
     * This Method is use to Set the Footer Menu Model Class
     *
     * @param footerMenuModel For Setting The Footer Menu Model Class
     */

    public void setFooterMenuModel(ArrayList<FooterMenu> footerMenuModel) {
        this.footerMenuModel = footerMenuModel;
    }

    String msg;
    ArrayList<MainMenu> mainMenuModel = new ArrayList<>();
    ArrayList<UserMenu> userMenuModel = new ArrayList<>();
    ArrayList<FooterMenu> footerMenuModel = new ArrayList<>();


    public class MainMenu {

        String title;
        String permalink;
        String id;
        String parent_id;
        String link_type;

        /**
         * This Method is use to Get the Main Menu Child Model Class
         *
         * @return mainMenuChildModel
         */

        public ArrayList<MainMenuChild> getMainMenuChildModel() {
            return mainMenuChildModel;
        }

        /**
         * This Method is use to Set the Main Menu Child Model Class
         *
         * @param mainMenuChildModel For Setting The Main Menu Child Model Class
         */

        public void setMainMenuChildModel(ArrayList<MainMenuChild> mainMenuChildModel) {
            this.mainMenuChildModel = mainMenuChildModel;
        }

        ArrayList<MainMenuChild> mainMenuChildModel = new ArrayList<>();


        public class MainMenuChild {

            String title;
            String permalink;
            String id;
            String parent_id;
            String link_type;

            /**
             * This Method is use to Get the Link type
             *
             * @return link_type
             */

            public String getLink_type() {
                return link_type;
            }

            /**
             * This Method is use to Set the Link type
             *
             * @param link_type For Setting The Link Type
             */

            public void setLink_type(String link_type) {
                this.link_type = link_type;
            }

            /**
             * This Method is use to Get the Title
             *
             * @return title
             */

            public String getTitle() {
                return title;
            }

            /**
             * This Method is use to Set the Title
             *
             * @param title For Setting The Title
             */

            public void setTitle(String title) {
                this.title = title;
            }

            /**
             * This Method is use to Get the Permalink
             *
             * @return permalink
             */

            public String getPermalink() {
                return permalink;
            }

            /**
             * This Method is use to Set the Permalink
             *
             * @param permalink For Setting the Permalink
             */

            public void setPermalink(String permalink) {
                this.permalink = permalink;
            }

            /**
             * This Method is use to Get the Id
             *
             * @return id
             */

            public String getId() {
                return id;
            }

            /**
             * This Method is use to Set the Id
             *
             * @param id For Setting The Id
             */

            public void setId(String id) {
                this.id = id;
            }

            /**
             * This Method is use to Get the Parents Id
             *
             * @return parent_id
             */

            public String getParent_id() {
                return parent_id;
            }

            /**
             * This Method is use to Set the Parents Id
             *
             * @param parent_id For Setting The Parents Id
             */

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }


        }

        /**
         * This Method is use to Get the Link type
         *
         * @return link_type
         */

        public String getLink_type() {
            return link_type;
        }

        /**
         * This Method is use to Set the Link type
         *
         * @param link_type For Setting The Link Type
         */

        public void setLink_type(String link_type) {
            this.link_type = link_type;
        }

        /**
         * This Method is use to Get the Title
         *
         * @return title
         */

        public String getTitle() {
            return title;
        }

        /**
         * This Method is use to Set the Title
         *
         * @param title For Setting The Title
         */

        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * This Method is use to Get the Permalink
         *
         * @return permalink
         */

        public String getPermalink() {
            return permalink;
        }

        /**
         * This Method is use to Set the Permalink
         *
         * @param permalink For Setting the Permalink
         */

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }

        /**
         * This Method is use to Get the Id
         *
         * @return id
         */

        public String getId() {
            return id;
        }

        /**
         * This Method is use to Set the Id
         *
         * @param id For Setting The Id
         */

        public void setId(String id) {
            this.id = id;
        }

        /**
         * This Method is use to Get the Parents Id
         *
         * @return parent_id
         */

        public String getParent_id() {
            return parent_id;
        }

        /**
         * This Method is use to Set the Parents Id
         *
         * @param parent_id For Setting The Parents Id
         */

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }


    }


    public class UserMenu {

        String title;
        String permalink;
        String parent_id;

        /**
         * This Method is use to Get the User Menu Child Model
         *
         * @return userMenuChildModel
         */

        public ArrayList<UserMenuChild> getUserMenuChildModel() {
            return userMenuChildModel;
        }

        /**
         * This Method is use to Set the User Menu Child Model
         *
         * @param userMenuChildModel For Setting The User Menu Child Model
         */

        public void setUserMenuChildModel(ArrayList<UserMenuChild> userMenuChildModel) {
            this.userMenuChildModel = userMenuChildModel;
        }

        ArrayList<UserMenuChild> userMenuChildModel = new ArrayList<>();


        public class UserMenuChild {


            String title;
            String permalink;
            String id;
            String parent_id;
            String link_type;

            /**
             * This Method is use to Get the Link Type
             *
             * @return link_type
             */

            public String getLink_type() {
                return link_type;
            }

            /**
             * This Method is use to Set the Link Type
             *
             * @param link_type For Setting The Link type
             */

            public void setLink_type(String link_type) {
                this.link_type = link_type;
            }

            /**
             * This Method is use to Get the Title
             *
             * @return title
             */

            public String getTitle() {
                return title;
            }

            /**
             * This Method is use to Set the Title
             *
             * @param title For Setting The Title
             */

            public void setTitle(String title) {
                this.title = title;
            }

            /**
             * This Method is use to Get the Permalink
             *
             * @return permalink
             */

            public String getPermalink() {
                return permalink;
            }

            /**
             * This Method is use to Set the Permalink
             *
             * @param permalink For Setting The Permalink
             */

            public void setPermalink(String permalink) {
                this.permalink = permalink;
            }

            /**
             * This Method is use to Get the Id
             *
             * @return id
             */

            public String getId() {
                return id;
            }

            /**
             * This Method is use to Set the Id
             *
             * @param id For Setting The Id
             */

            public void setId(String id) {
                this.id = id;
            }

            /**
             * This Method is use to Get the Parent Id
             *
             * @return parent_id
             */

            public String getParent_id() {
                return parent_id;
            }

            /**
             * This Method is use to Set the Parent Id
             *
             * @param parent_id For Setting The Parent Id
             */

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }
        }

        /**
         * This Method is use to Get the Title
         *
         * @return title
         */

        public String getTitle() {
            return title;
        }

        /**
         * This Method is use to Set the Title
         *
         * @param title For Setting The Title
         */

        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * This Method is use to Get the Permalink
         *
         * @return permalink
         */

        public String getPermalink() {
            return permalink;
        }

        /**
         * This Method is use to Set the Permalink
         *
         * @param permalink For Setting The Permalink
         */

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }


        /**
         * This Method is use to Get the Parent Id
         *
         * @return parent_id
         */

        public String getParent_id() {
            return parent_id;
        }

        /**
         * This Method is use to Set the Parent Id
         *
         * @param parent_id For Setting The Parent Id
         */

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }
    }


    public class FooterMenu {

        String domain;
        String link_type;
        String id;
        String display_name;
        String permalink;
        String url;

        /**
         * This Method is use to Get the URL
         *
         * @return url
         */

        public String getUrl() {
            return url;
        }

        /**
         * This Method is use to Set the URL
         *
         * @param url For Setting The URL
         */

        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * This Method is use to Get the Domain
         *
         * @return domain
         */

        public String getDomain() {
            return domain;
        }

        /**
         * This Method is use to Set the Domain
         *
         * @param domain For Setting The Domain
         */

        public void setDomain(String domain) {
            this.domain = domain;
        }

        /**
         * This Method is use to Get the Link Type
         *
         * @return link_type
         */

        public String getLink_type() {
            return link_type;
        }

        /**
         * This Method is use to Set the Link Type
         *
         * @param link_type For Setting The Link Type
         */

        public void setLink_type(String link_type) {
            this.link_type = link_type;
        }

        /**
         * This Method is use to Get the Id
         *
         * @return id
         */

        public String getId() {
            return id;
        }

        /**
         * This Method is use to Set the Id
         *
         * @param id For Setting The Id
         */

        public void setId(String id) {
            this.id = id;
        }

        /**
         * This Method is use to Get the Display Name
         *
         * @return display_name
         */

        public String getDisplay_name() {
            return display_name;
        }

        /**
         * This Method is use to Set the Display Name
         *
         * @param display_name For Setting The Display Name
         */

        public void setDisplay_name(String display_name) {
            this.display_name = display_name;
        }

        /**
         * This Method is use to Get the Permalink
         *
         * @return permalink
         */

        public String getPermalink() {
            return permalink;
        }

        /**
         * This Method is use to Set the Permalink
         *
         * @param permalink For Setting The Permalink
         */

        public void setPermalink(String permalink) {
            this.permalink = permalink;
        }


    }


}
