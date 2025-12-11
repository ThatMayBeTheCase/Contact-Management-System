public class Address {
    // TODO: fields: streetName, streetNumber, city, postalCode

        // Fields of the Address class
        private String city;
        private String postalCode;
        private String streetName;
        private String portNumber;

        // Constructor of the Address class
        public Address(String city, String postalCode, String streetName, String portNumber) {
            this.city = city;
            this.postalCode = postalCode;
            this.streetName = streetName;
            this.portNumber = portNumber;

        }
             //Getter and Setter methods for each field
        public String getCity() {
            return city;
        }
        public String getPostalCode() {
            return postalCode;
        }
        public String getStreetName() {
            return streetName;
        }
        public String getPortNumber() {
            return portNumber;
        }

        // Setter methods
        public void setCity(String city) {
            this.city = city;

        }
        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }
        public void setPortNumber(String portNumber) {
            this.portNumber = portNumber;
        }

        // This method checks if the search term matches any field
        public boolean matches(String searchTerm) {
            String lowerCaseTerm = searchTerm.toLowerCase();
            return city.toLowerCase().contains(lowerCaseTerm) ||
                   postalCode.toLowerCase().contains(lowerCaseTerm) ||
                   streetName.toLowerCase().contains(lowerCaseTerm) ||
                   portNumber.toLowerCase().contains(lowerCaseTerm);
        }

        @Override
        public String toString() {
            return streetName + " " + portNumber + ", " + postalCode + " " + city;
        }

    }

