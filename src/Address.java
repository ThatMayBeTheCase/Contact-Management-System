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

            //
        }
        @Override
        public String toString() {
            return streetName + " " + portNumber + ", " + postalCode + " " + city;
        }

    }

