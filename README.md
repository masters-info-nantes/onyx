# onyx
## Extensible platform build for plugin loading
The app build for this platform is a micro OS developed in java.


At first, we do "git clone https://github.com/masters-info-nantes/onyx.git" on the terminal to clone the project on your computer.

To show the proxy of your computer, you type "echo $http_proxy".
To be sure that the proxy is correct you type "gedit /.m2/settings.xml".
If your proxy and the port that you seen with the command "echo $http_proxy" are not like the proxy and the port which are in the file settings.xml, you have to modify the one in the file settings.xml.

This is the file settings.xml after the modification with the proxy of the university:
```
  <settings>
    <proxies>
      <proxy>
        <id>example-proxy</id>
        <active>true</active>
        <protocol>http</protocol>
        <host>proxy.ensinfo.sciences.univ-nantes.prive</host>
        <port>3128</port>
      </proxy>
    </proxies>
  </settings>
```

Then you do the command "cd 'way to your folder'" to place you in the folder where you cloned the project.
Type the command "mvn clean install".

Then type "cd onyx-emulator/"

To run it type "mvn exec:java-X"
