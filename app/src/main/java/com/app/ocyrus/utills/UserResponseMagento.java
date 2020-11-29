package com.app.ocyrus.utills;

public class UserResponseMagento {

        /**
         * token : zxkvus7vigvmh54irvkq7a3pwdwwgdlp
         * user : {"id":3,"username":null,"first_name":"Mayank","last_name":"Misra","email":"mayankmisra@mailinator.com","status":1,"telephone_number":null,"address_1":"","address_2":"","profile_picture":null,"background_image":null,"about_me":null,"confirmation_code":"","confirmed":1,"is_email_verified":0,"is_term_accept":"","created_by":"","updated_by":"","deleted_at":"","is_deleted":0}
         */

        private String token;
        private UserBean user;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 3
             * username : null
             * first_name : Mayank
             * last_name : Misra
             * email : mayankmisra@mailinator.com
             * status : 1
             * telephone_number : null
             * address_1 :
             * address_2 :
             * profile_picture : null
             * background_image : null
             * about_me : null
             * confirmation_code :
             * confirmed : 1
             * is_email_verified : 0
             * is_term_accept :
             * created_by :
             * updated_by :
             * deleted_at :
             * is_deleted : 0
             */

            private int id;
            private Object username;
            private String first_name;
            private String last_name;
            private String email;
            private int status;
            private Object telephone_number;
            private String address_1;
            private String address_2;
            private Object profile_picture;
            private Object background_image;
            private Object about_me;
            private String confirmation_code;
            private int confirmed;
            private int is_email_verified;
            private String is_term_accept;
            private String created_by;
            private String updated_by;
            private String deleted_at;
            private int is_deleted;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getUsername() {
                return username;
            }

            public void setUsername(Object username) {
                this.username = username;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public Object getTelephone_number() {
                return telephone_number;
            }

            public void setTelephone_number(Object telephone_number) {
                this.telephone_number = telephone_number;
            }

            public String getAddress_1() {
                return address_1;
            }

            public void setAddress_1(String address_1) {
                this.address_1 = address_1;
            }

            public String getAddress_2() {
                return address_2;
            }

            public void setAddress_2(String address_2) {
                this.address_2 = address_2;
            }

            public Object getProfile_picture() {
                return profile_picture;
            }

            public void setProfile_picture(Object profile_picture) {
                this.profile_picture = profile_picture;
            }

            public Object getBackground_image() {
                return background_image;
            }

            public void setBackground_image(Object background_image) {
                this.background_image = background_image;
            }

            public Object getAbout_me() {
                return about_me;
            }

            public void setAbout_me(Object about_me) {
                this.about_me = about_me;
            }

            public String getConfirmation_code() {
                return confirmation_code;
            }

            public void setConfirmation_code(String confirmation_code) {
                this.confirmation_code = confirmation_code;
            }

            public int getConfirmed() {
                return confirmed;
            }

            public void setConfirmed(int confirmed) {
                this.confirmed = confirmed;
            }

            public int getIs_email_verified() {
                return is_email_verified;
            }

            public void setIs_email_verified(int is_email_verified) {
                this.is_email_verified = is_email_verified;
            }

            public String getIs_term_accept() {
                return is_term_accept;
            }

            public void setIs_term_accept(String is_term_accept) {
                this.is_term_accept = is_term_accept;
            }

            public String getCreated_by() {
                return created_by;
            }

            public void setCreated_by(String created_by) {
                this.created_by = created_by;
            }

            public String getUpdated_by() {
                return updated_by;
            }

            public void setUpdated_by(String updated_by) {
                this.updated_by = updated_by;
            }

            public String getDeleted_at() {
                return deleted_at;
            }

            public void setDeleted_at(String deleted_at) {
                this.deleted_at = deleted_at;
            }

            public int getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(int is_deleted) {
                this.is_deleted = is_deleted;
            }
        }

}
